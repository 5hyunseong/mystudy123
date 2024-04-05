package com.ohgiraffers.mybatistojpa.fileupload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class FileUploadController {

    @Autowired
    private ResourceLoader resourceLoader;

    @PostMapping("single-file")
    public String singleFileUpload(@RequestParam MultipartFile singleFile,
                                   @RequestParam String singleFileDescription,
                                   RedirectAttributes rAttr) throws IOException {
        /* 설명. 클라이언트로부터 넘어온 multipart form 데이터를 확인.*/
        System.out.println("singleFile = " + singleFile);
        System.out.println("singleFileDescription = " + singleFileDescription);

        /* 설명. build 경로의 static에 업로드한 파일이 저장될 경로를 설정해야 한다.
         *   1. /src/main/resources/static/ 경로에 아무 것도 없는 것을 확인.
         *   2. 해당 경로 하위로 디렉토리를 생성:
         *   -src/main/resoruces/static/uploadedFiles/img/single
         *   -src/main/resources/static/uploadedFiles/img/multi
         *   3. 내장 톰캣을 실행해 프로젝트를 빌드한다.
         *      이때, build 디렉토리 하위로 /src 디렉토리에서 만든 구조가 반영되는 것을 확인할 수 있다.
         *      - build/resources/main/static/uploadedFiles/img/single
         *      - build/resources/main/static/uploadedFiles/img/multi
         *  */

        Resource resource = resourceLoader.getResource("classpath:static/uploadedFiles/img/single");
        String filePath = null;

        /* 설명. 파일을 저장할 경로(=위에서 뽑아온 resources)가 존재하지 않을 경우:
         *   디렉토리를 직접 생성해서 해당 경로를 잡아줄 수 있다.
         *   하지만, 우리는 내장 톰캣을 실행해 build 디렉토리 하위에 저장 경로가 생성되는 것을 이미 확인했기 때문에
         *   아래 작성한 if() 조건문은 동작하지 않을 것이라고 확신하고 넘어가면 된다.
         *   즉, Spring이 resources를 읽어와야 하는데, 어떠한 이유로 경로가 생성되지 않았을 경우 아래와 같은 로직을 추가하면
         *   혹시 모를 런타임 에러를 방지할 수 있다.
         * */
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

        String savedName = UUID.randomUUID().toString().replace("-", "") + extension;
        System.out.println("저장될 파일 이름 = " + savedName);

        /* 설명. 파일 저장.
         *   해당 메소드에 이미 IOException 예외 처리를 해주었지만, 파일을 업로드하는 과정에서 에러가 발생하면
         *   이미 저장된 파일을 삭제한 후 실패 페이지로 보내줘야 하기 때문에 의도적으로 try-catch문으로 감싸줘야 한다.
         * */

        try{ // 로직이 실행되는 블럭

            /* 설명. 실제로 파일이 저장되는 명령어.*/
            singleFile.transferTo(new File(filePath + "/" + savedName));

            /* 설명. 실제로 이 부분에서 DB를 다녀오는 Service 계층(비즈니스 로직)이 작성되는 위치다. */
            // DO SOMTHING...

            rAttr.addFlashAttribute("message", "[SUCCESS] 단일 파일 업로드 성공!");
            rAttr.addFlashAttribute("img", "static/uploadedFiles/img/single" + "/" + savedName);
            rAttr.addFlashAttribute("singleFileDescription", singleFileDescription);

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



        return "redirect:/result";
    }
}
