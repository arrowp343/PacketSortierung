public class Tools {
    public static char[] generateCharPool(char start, char end){
        if((int) start > (int) end){
            char temp = end;
            end = start;
            start = temp;
        }
        int length = (int) end - (int) start + 1;
        char[] charPool = new char[length];
        for(int i = 0; i < length; i++)
            charPool[i] = (char) ((int) start + i);
        return charPool;
    }
    public static char[] connectCharPool(char[] pool1, char[] pool2){
        char[] pool12 = new char[pool1.length + pool2.length];
        int i = 0, j = 0;
        for(; i < pool1.length; i++)
            pool12[i] = pool1[i];
        for(; j < pool2.length; j++)
            pool12[i+j] = pool2[j];
        return pool12;
    }
}