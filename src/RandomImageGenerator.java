import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class RandomImageGenerator {

    public BufferedImage createImage(String key){

        int size = 8;
        int size_out = 600;

        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);

        ArrayList<Integer> numbers = stringToInt(key);

        for(int y = 0; y < size; y++){
            for(int x = 0; x < size; x++){

                int a = 255; //alpha
                int r = (int)(Math.abs((Math.cos(numbers.get(0)*x)+1)/2)*256); //red
                int g = (int)(Math.abs((Math.sin(numbers.get(1)*y)+1)/2)*256); //green
                int b = (int)(Math.abs((Math.cos(numbers.get(2)*x*y)+1)/2)*256); //blue
                int p = (a<<24) | (r<<16) | (g<<8) | b; //pixel

                img.setRGB(x, y, p);
            }
        }

        BufferedImage img2 = new BufferedImage(size_out, size_out, BufferedImage.TYPE_INT_ARGB);

        for(int y = 0; y < size; y++){
            for(int x = 0; x < size; x++){

                int p = img.getRGB(x,y);

                for(int a = 0; a < size_out/size; a++){
                    for(int b = 0; b < size_out/size; b++)
                    {
                        img2.setRGB(a+(x*size_out/size), b+(y*size_out/size), p);
                    }
                }

            }
        }

        return img2;
    }


    private ArrayList<Integer> stringToInt(String key){
        ArrayList<Integer> numbers = new ArrayList<>();
        int sum = 0;
        int pow = 1;
        for(int x = 0; x<key.length(); x++){
            sum+=(int)key.charAt(x);
            pow=pow^(int)key.charAt(x);
        }
        numbers.add(sum);
        numbers.add(pow);
        numbers.add(sum/key.length());
        return numbers;
    }
}