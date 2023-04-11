class Main {
    public static void main(String[] args) {
        Decode decoder = new Decode();
        
        decoder.input(0);
        decoder.input(0);
        decoder.input(0);
        decoder.input(0);
        decoder.input(1);
        decoder.input(0);
        decoder.input(0);
        decoder.input(0);
        decoder.input(0);
        decoder.input(1);
        decoder.input(1);
        decoder.input(0);
        decoder.input(0);
        decoder.input(1);
        decoder.input(0);
        decoder.input(1);
        decoder.input(1);
        decoder.input(1);
        decoder.input(0);
        decoder.input(1);
        decoder.input(0);
        
        System.out.println(decoder.output());
        
        decoder.reset();
        
        decoder.input(1);
        decoder.input(1);
        decoder.input(0);
        decoder.input(1);
        decoder.input(1);
        decoder.input(1);
        decoder.input(1);
        decoder.input(0);
        decoder.input(0);
        decoder.input(0);
        decoder.input(0);
        decoder.input(0);
        decoder.input(0);
        decoder.input(0);
        decoder.input(0);
        decoder.input(1);
        decoder.input(1);
        decoder.input(0);
        decoder.input(1);
        decoder.input(1);
        decoder.input(1);
        decoder.input(1);
        decoder.input(1);
        decoder.input(1);
        decoder.input(0);
        decoder.input(0);
        decoder.input(1);
        decoder.input(1);
        decoder.input(0);
        
        System.out.println(decoder.output());
        
        decoder.reset();
        
        System.out.println(decoder.output());
        
        System.out.println("here");
    }
}
