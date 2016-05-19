package com.andersoncarvalho.pidtp.controller;

/**
 * Created by anderson on 02/03/16.
 */
    import com.andersoncarvalho.pidtp.controller.data.DataData;
    import com.andersoncarvalho.pidtp.controller.data.DataResponse;
    import com.andersoncarvalho.pidtp.dao.DAO;
    import com.andersoncarvalho.pidtp.entity.Imagem;
    import com.andersoncarvalho.pidtp.model.RICModel;
    import org.omg.PortableServer.REQUEST_PROCESSING_POLICY_ID;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.context.annotation.Scope;

    import org.springframework.stereotype.Controller;

    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.multipart.MultipartFile;


    import javax.servlet.ServletContext;
    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpSession;
    import javax.swing.*;
    import java.io.IOException;

    import java.net.URL;
    import java.sql.SQLException;
    import java.util.List;


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
            return "{\"success\": true, \"message\": \"" + mensagemRetorno + "\"}";
    }

    @RequestMapping(value="/calcDist",method = RequestMethod.POST)
    @ResponseBody
    public DataResponse calcDist(HttpServletRequest request, @RequestParam int tipoDistancia){
        try{
            DataData dt = new DataData();
            String caminhoPadrao = request.getSession().getServletContext().getRealPath("/") + "assets/exemplos/";
            if(tipoDistancia == 2) {
                RICModel.calcEuclidiana(caminhoPadrao);
            }else if(tipoDistancia == 3){
                RICModel.calcXadrez(caminhoPadrao);
            }else if(tipoDistancia == 4){
                RICModel.calcCoseno(caminhoPadrao);
            }else{
                RICModel.calcManhattan(caminhoPadrao);
            }
            dt.setMessage("Histogramas obtidos com sucesso");
            return dt;
        }catch (Exception ex){
            return DataResponse.ERROR.setMessage("Erro ao processar a imagem").setImportant(true);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public DataResponse listaImagens(){
        DataData dados = new DataData(true);
        dados.add(RICModel.listarImagensOrdenadas());
        return dados;
    }

    @RequestMapping(value="/uploadimg",method = RequestMethod.POST)
    @ResponseBody
    public DataResponse uploadImagem(HttpServletRequest request, @RequestParam String dataURL){
        try{
            DataData dt = new DataData();
            String caminhoPadrao = request.getSession().getServletContext().getRealPath("/") + "assets/exemplos/";
            RICModel.uploadImagem(caminhoPadrao,dataURL);
            dt.setMessage("Upload realizado com sucesso");
            return dt;
        }catch (Exception ex){
            return DataResponse.ERROR.setMessage("Erro ao processar a imagem").setImportant(true);
        }
    }

    @RequestMapping(value="/filtro",method = RequestMethod.POST)
    @ResponseBody
    public DataResponse filtro(HttpServletRequest request, @RequestParam String nomeImagem, @RequestParam int tipo){
        try{
            DataData dt = new DataData();
            String caminhoPadrao = request.getSession().getServletContext().getRealPath("/") + "assets/exemplos/";
            Imagem img;
            if(tipo == 1) {
                img = RICModel.filtroMedia(caminhoPadrao, nomeImagem);
                dt.setMessage("Filtro efetuado Média com sucesso");
            }else if (tipo == 2){
                img = RICModel.filtroMediana(caminhoPadrao,nomeImagem);
                dt.setMessage("Filtro efetuado Mediana com sucesso");

            }else if (tipo == 3){
                img = RICModel.filtroMaximo(caminhoPadrao,nomeImagem);
                dt.setMessage("Filtro efetuado Maximo com sucesso");
            }else if (tipo == 4){
                img = RICModel.filtroMinimo(caminhoPadrao,nomeImagem);
                dt.setMessage("Filtro efetuado Minimo com sucesso");
            }else{
                img = RICModel.transformadaBinarizacao(caminhoPadrao,nomeImagem);
                dt.setMessage("Transformada efetuada com sucesso");
            }
            if(img != null) {
                dt.add(img);
                return dt;

            }else{
                return DataResponse.ERROR.setMessage("Erro ao processar a imagem").setImportant(true);

            }
        }catch (Exception ex){
            return DataResponse.ERROR.setMessage("Erro ao processar a imagem").setImportant(true);
        }
    }

    @RequestMapping(value="/processar",method = RequestMethod.POST)
    @ResponseBody
    public DataResponse processarImagem(HttpServletRequest request, @RequestParam String nomeImagem){
        try{
            String caminhoPadrao = request.getSession().getServletContext().getRealPath("/") + "assets/exemplos/";
            RICModel.processarImagem(caminhoPadrao, nomeImagem,false);
            return DataResponse.SUCCESS.setMessage("Imagem processada com sucesso");
        }catch (Exception ex){
            return DataResponse.ERROR.setMessage("Erro ao processar a imagem").setImportant(true);
        }
    }

    @RequestMapping(value="/histograma",method = RequestMethod.POST)
    @ResponseBody
    public DataResponse getHistograma(HttpServletRequest request, @RequestParam String nomeImagem, @RequestParam boolean normalizar){
        try{
            DataData dt = new DataData();
            String caminhoPadrao = request.getSession().getServletContext().getRealPath("/") + "assets/exemplos/";
            dt.add(RICModel.getHistogramaRGB(caminhoPadrao,nomeImagem,normalizar));
            dt.add(RICModel.getHistogramaHSV(caminhoPadrao,nomeImagem,normalizar));
            dt.add(RICModel.getHistogramaYUV(caminhoPadrao,nomeImagem,normalizar));
            dt.setMessage("Histogramas obtidos com sucesso");
            return dt;
        }catch (Exception ex){
            return DataResponse.ERROR.setMessage("Erro ao processar a imagem").setImportant(true);
        }
    }

    @RequestMapping(value="/calculardados",method = RequestMethod.GET)
    @ResponseBody
    public DataResponse calcularDadosDoBanco(HttpServletRequest request){
        try{
            String caminhoPadrao = request.getSession().getServletContext().getRealPath("/") + "assets/exemplos/";
            RICModel.calcularDadosDoBanco(caminhoPadrao);
            return DataResponse.SUCCESS.setMessage("Imagems calculadas com sucesso");
        }catch (Exception ex){
            return DataResponse.ERROR.setMessage("Erro ao processar a imagem").setImportant(true);
        }
    }
}
