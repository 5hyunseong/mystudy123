package com.ohgiraffers.mybatistojpa.menu.controller;

import com.ohgiraffers.mybatistojpa.menu.dto.CategoryDTO;
import com.ohgiraffers.mybatistojpa.menu.dto.MenuDTO;
import com.ohgiraffers.mybatistojpa.menu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/menu")
public class MenuController {


    @Autowired
    private ResourceLoader resourceLoader;
    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/regist")
    public void regist(){}


    @GetMapping(value = "/category", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<CategoryDTO> findAllCategory(){
        System.out.println("호출!!");
        return menuService.findAllCategory();
    }

    @GetMapping("list")
    public String findMenuList(Model model){

        List<MenuDTO> menuList = menuService.findMenuList();

        for (MenuDTO menuDTO: menuList){
            menuDTO.setImg("static/uploadedFiles/img/single/" + menuDTO.getMenuPictureName() + menuDTO.getMenuPictureExtension());
        }

        model.addAttribute("menuList", menuList);

        return "menu/list";
    }







    @GetMapping("/detail/{code}")
    public String findMenuDetail(@PathVariable("code") int code,
                                 Model model){

        System.out.println("컨트롤러에 detail get 요청 잘 날아옴");
        MenuDTO menu = menuService.findMenuCode(code);

        menu.setImg("static/uploadedFiles/img/single/" + menu.getMenuPictureName() + menu.getMenuPictureExtension());
        model.addAttribute("menu", menu);


        return "menu/detail";
    }




    @GetMapping("edit/{code}")
    public String modifyMenu(@PathVariable("code") int code, Model model){

        MenuDTO menu = menuService.findMenuCode(code);

        model.addAttribute("menu", menu);

        return "menu/edit";
    }

    @GetMapping("edit")
    public String editPage(@RequestParam("codeNum") int codeNum){
        return "redirect:/menu/edit/" + codeNum;
    }

    @PostMapping("/regist")
    public String registNewMenu(MenuDTO menu,
                                Model model,
                                @RequestParam MultipartFile singleFile,
                                RedirectAttributes rAttr)
            throws IOException {

        /* 설명. 파일 업로드*/
        Resource resource = resourceLoader.getResource("classpath:static/uploadedFiles/img/single");
        String filePath = null;


        if(!resource.exists()){
            String root = "src/main/resources/static/uploadedFiles/img/single";
            File file = new File(root);
            file.mkdirs();

            /* 설명. getAbsolutePath()는 IOException 처리해야 함*/
            filePath = file.getAbsolutePath();
        }else{
            filePath = resourceLoader.getResource("classpath:static/uploadedFiles/img/single")
                    .getFile()
                    .getAbsolutePath();
        }

        /* 설명. 실제 파일이 저장될 경로를 확인.*/
        System.out.println("빌드된 single 디렉토리의 절대 경로 = " + filePath);

        /* 설명. 파일명 변경 처리*/
        String originalFileName = singleFile.getOriginalFilename();
        System.out.println("원래 파일 이름 = " + originalFileName);

        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        System.out.println("파일 확장자 = " + extension);


        String savedName = UUID.randomUUID().toString().replace("-", "") ;
        System.out.println("저장될 파일 이름 = " + savedName);

        /* 설명. 파일 저장.
         *   해당 메소드에 이미 IOException 예외 처리를 해주었지만, 파일을 업로드하는 과정에서 에러가 발생하면
         *   이미 저장된 파일을 삭제한 후 실패 페이지로 보내줘야 하기 때문에 의도적으로 try-catch문으로 감싸줘야 한다.
         * */

        try{ // 로직이 실행되는 블럭

            /* 설명. 실제로 파일이 저장되는 명령어.*/
            singleFile.transferTo(new File(filePath + "/" + savedName + extension));

            /* 설명. 실제로 이 부분에서 DB를 다녀오는 Service 계층(비즈니스 로직)이 작성되는 위치다. */
            // DO SOMTHING...

            rAttr.addFlashAttribute("message", "[SUCCESS] 단일 파일 업로드 성공!");

            System.out.println("[IntelliJ] [SUCCESS] 단일 파일 업로드 성공!");
        }catch (IOException e){ // try문에서 예외가 발생했을 때 해당 블럭으로 이동됨(즉 예외를 처리하는 영역)

            /* 설명. try 구문 안, 즉 비즈니스 로직 처리 도중 예외가 발생했다면 아마 파일만 업로드 되어 있을 것이다.
             *   그 파일은 주인이 없는 (missing) 파일이 되어 쓰레기처럼 서버에 쌓일 것이기 때문에 이를 삭제해줘야 한다.
             * */
            new File(filePath + "/" + savedName).delete();

            e.printStackTrace();

            rAttr.addFlashAttribute("message", "[SUCCESS] 단일 파일 업로드 실패!");

            System.out.println("[IntelliJ] [FAILED] 단일 파일 업로드 실패!");
        }

        menu.setMenuPictureName(savedName);
        System.out.println("savedName = " + savedName);
        menu.setMenuPictureExtension(extension);

        menu.setImg("static/uploadedFiles/img/single" + "/" + savedName + extension);

        /* 설명. menu 추가*/
        System.out.println("menu = " + menu);
        System.out.println("컨트롤러 post요청 도달");
        menuService.registMenu(menu);
        List<MenuDTO> menuList = menuService.findMenuList();

        model.addAttribute(menuList);

        return "redirect:/menu/list";
    }






//    @PostMapping("update")
//    public String list(){
//        System.out.println("update 도착!");
//        return "menu/list";
//    }

    @PostMapping("/modify")
    public String modifyMenu(MenuDTO modifyMenu){

        menuService.modifyMenu(modifyMenu);

        return "redirect:/menu/detail/" + modifyMenu.getMenuCode();
    }




//    @PostMapping("/delete/{code}")
//    public String deleteMenu(@PathVariable int code){
//
//        menuService.deleteMenu(code);
//
//        return "redirect:/menu/list";
//    }

    @GetMapping("delete")
    public void delete(){}

//    @PostMapping("/delete")
//    public String deleteMenu(@RequestParam Integer no){
//        menuService.deleteMenu(no);
//
//        return "redirect:/menu/list";
//    }


//    @PostMapping("modify")
//    public String editPage(@PathVariable int code){
//
//        return "";
//    }





}
