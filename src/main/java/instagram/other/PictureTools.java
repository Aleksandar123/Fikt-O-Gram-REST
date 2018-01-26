package instagram.other;

import org.apache.tika.Tika;
import org.apache.tomcat.util.codec.binary.Base64;
import org.imgscalr.Scalr;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by aleksandar on 24.1.17.
 */
public class PictureTools {
    public String cropImage(File image){
        byte[] bytes = new byte[0];
        try {
            BufferedImage bimg = ImageIO.read(image);


            int origWidth = bimg.getWidth();
            int origHeight = bimg.getHeight();

            //Crop Image

            System.out.println("Width: " + origWidth + "\nHeight: " + origHeight + "\nImage orientation: " + PictureTools.imageOrientation(origWidth,origHeight));

            System.out.println("Cropping ...");
            BufferedImage newImage = Scalr.resize(bimg,640);

            int cropedWidth = newImage.getWidth();
            int cropedHeight = newImage.getHeight();


            System.out.println("New Width: " + cropedWidth + "\nNew Height: " + cropedHeight + "\nImage orientation: " + imageOrientation(origWidth,origHeight));

            //Convert to byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(newImage, getFormat(detectType(image)), baos);
            bytes = baos.toByteArray();


            System.out.println("Byte Array length: " + bytes.length + "\n");

            System.out.println("BASE64: ");
            //Convert to base64
            String testBase = Base64.encodeBase64String(bytes);

            String base64Data = "data:image/" + getFormat(image.getAbsolutePath()) +";base64," +testBase;

            File delete = new File(image.getAbsolutePath());
            delete.delete();

            return base64Data;

        }catch (Exception e){
            System.out.println("Error while cropping image");
        }

        return "error";
    }

    //Detect file type
    //Example: For png file returns image/png
    public  String detectType(File file) throws IOException {

        return new Tika().detect(file);
    }


    //Get the extention from the detectType method.
    public String getFormat(String imageName)
    {
        if(imageName.endsWith("png")){
            return "png";
        }
        if(imageName.endsWith("jpeg")){
            return "jpg";
        }
        if(imageName.endsWith("gif")){
            return "gif";
        }
        if(imageName.endsWith("jpg")){
            return "jpg";
        }

        return "not-allowed";
    }

    public static String imageOrientation(int width, int height){

        if(width>height){
            return "horizontal";
        }else if (width<height){
            return "vertical";
        }else if(width==height){
            return "square";
        }
        return "it shouldn't get here..";
    }

    public File convert(MultipartFile file)
    {

        File convFile = new File(file.getOriginalFilename());
        try{
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
            return convFile;}
        catch (IOException e ){
            System.out.println("IO exeption in convert file ");
        }
        return convFile;
    }

}
