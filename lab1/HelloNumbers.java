import org.ietf.jgss.Oid;

public class HelloNumbers{
    public static void main(String[] args){
        int x=0,sum=0;
        while(x<10){
            System.out.println(sum+" ");
            x++;
            sum+=x;
        }
    }
}