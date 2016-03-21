package com.andersoncarvalho.pidtp.controller;

/**
 * Created by anderson on 02/03/16.
 */
    import com.andersoncarvalho.pidtp.controller.data.DataData;
    import com.andersoncarvalho.pidtp.controller.data.DataResponse;
    import com.andersoncarvalho.pidtp.dao.DAO;
    import com.andersoncarvalho.pidtp.entity.Imagem;
    import com.andersoncarvalho.pidtp.model.RICModel;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.context.annotation.Scope;

    import org.springframework.stereotype.Controller;

    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.multipart.MultipartFile;


    import java.io.IOException;

    import java.sql.SQLException;


@Controller
@Scope("prototype")
@RequestMapping("/ric")
public class RICController extends AbstractController {
    @Autowired
    private RICModel RICModel;
    @Autowired
    private DAO DAO;

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


    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public DataResponse listaImagens(){
        DataData dados = new DataData(true);
        dados.add(DAO.findAll(Imagem.class));
        return dados;
    }

}
