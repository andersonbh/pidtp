package com.andersoncarvalho.pidtp.controller;

/**
 * Created by anderson on 02/03/16.
 */
    import com.andersoncarvalho.pidtp.dao.DAO;
    import com.andersoncarvalho.pidtp.model.RTCModel;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.context.annotation.Scope;

    import org.springframework.stereotype.Controller;

    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.multipart.MultipartFile;


    import javax.sql.rowset.serial.SerialBlob;
    import java.io.IOException;

    import java.sql.SQLException;


@Controller
@Scope("prototype")
@RequestMapping("/rtc")
public class RTCController extends AbstractController {
    @Autowired
    private RTCModel rtcModel;
    @Autowired
    private DAO dao;

//    Fazer o get das imagens do banco do professor

    @RequestMapping(method = RequestMethod.POST)
    public
    @ResponseBody
    String manipularImagem(@RequestParam(required = false) MultipartFile arquivo) throws SQLException {

            String mensagemRetorno = "";
//            Imagem imagem = new Imagem();
            if (arquivo != null) {
                if (!arquivo.isEmpty()) {
                    byte[] bytes;
                    try {
                        bytes = arquivo.getBytes();
//                        Arquivo arquivoTemp = new Arquivo(arquivo.getOriginalFilename(), arquivo.getContentType(), new SerialBlob(bytes));

//                        imagem.setArquivo(arquivoTemp);
                    } catch (IOException e) {
                        mensagemRetorno += " Arquivo n&#227o pode ser anexado na imagem";   // &#227 -> ã
                    }
                }
            }
//            rtcModel.manipularImagem(imagem);
//            mensagemRetorno += " ";   // &#250 -> ú
            return "{\"success\": true, \"message\": \"" + mensagemRetorno + "\"}";


    }
}
