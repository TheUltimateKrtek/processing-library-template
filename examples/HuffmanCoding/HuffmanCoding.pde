void setup(){
  boolean[] result = HuffmanCoding.Encoding.encode("Hello world!", 1);
  for(int i = 0; i < result.length; i ++){
    Console.print(result[i] ? 1 : 0, null, result[i] ? Console.Color4.light_cyan : Console.Color4.light_red);
  }
  Console.println();
  boolean[] decoded = HuffmanCoding.Decoding.decode(result);
  for(int i = 0; i < decoded.length; i ++){
    Console.print(decoded[i] ? 1 : 0, null, decoded[i] ? Console.Color4.light_cyan : Console.Color4.light_red);
    if(i % 8 == 7) Console.print(" ");
  }
  Console.println();
  for(int i = 0; i < decoded.length / 8; i ++){
    int value = 0;
    int mult = 1;
    for(int j = 0; j < 8; j ++){
      if(decoded[(i + 1) * 8 - 1 - j]) value += mult;
      mult *= 2;
    }
    Console.print((char)value + "        ");
  }
  Console.println();
}
void draw(){

}