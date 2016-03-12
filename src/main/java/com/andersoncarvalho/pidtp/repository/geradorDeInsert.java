package com.andersoncarvalho.pidtp.repository;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Classe para gerar os inserts no banco de dados baseado nas imagens passadas pelo professor
 * Esse metodo é meio gambiarra mas faz o que precisamos no tempo
 * Futuramente podemos gerar um arquivo sql..
 */

public class geradorDeInsert {
    public static void main(String[] args) throws IOException {

        for(int i = 0; i < 1000; i++){
            BufferedImage bimg = ImageIO.read(new File("/Users/anderson/Projetos/pidtp/src/main/webapp/assets/exemplos/" + i +".jpg"));
            int width          = bimg.getWidth();
            int height         = bimg.getHeight();
            System.out.println(i + ";../../../assets/exemplos/"+ i + ".jpg;" + i + ";" + width + ";" + height);
//            Para gerar para inserir o sql direto descomente a linha abaixo
//          System.out.println("insert into imagem VALUES ("+ i + ", '../../../assets/exemplos/"+ i + ".jpg', '" + i + "', " + width + ", " + height + ");");
        }
        System.out.println("AWWWWW");
    }
}
