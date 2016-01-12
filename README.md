MagicStringHider
=================================

# Introduction

**MagicStringHider** is a Java library which is used for hiding any string by converting it into another string composed of A-Z characters. The hidden string is not unique. One original string can be converted to different strings. but they can be recovered to the same original string. You can create your hidden string by adding any key to protect it from someone who has **MagicStringHider** library, too.

# Usage

## StringHider Class

**StringHider** class is in the *org.magiclen.magicstringhider* package. It can help you hide your string.

### Initialize

You don't need to initialize **StringHider** class. Just use its static methods to do what you want.

You can use **hideString**, **compression**, **decompression** and **recoverString** static methods in  **StringHider** class to hide/compress/compress/decompression your strings.

### Hide/Recover/Compress/Decompression strings

For example,

    final String originalString = "This is my secret text. No one except me can read it. My name is Magic Len, and I'm a 台灣人.";
    final String key = "My hiding key";
    final String originalHiddenString = StringHider.hideString(originalString, key);
    final String compressedHiddenString = StringHider.compression(originalHiddenString);
    final String decompressedHiddenString = StringHider.decompression(compressedHiddenString);
    final String recoveredString = StringHider.recoverString(decompressedHiddenString, key);
    System.out.println("originalString = " + originalString);
    System.out.println("originalHiddenString = " + originalHiddenString);
    System.out.println("compressedHiddenString = " + compressedHiddenString);
    System.out.println("decompressedHiddenString = " + decompressedHiddenString);
    System.out.println("recoveredString = " + recoveredString);

The result is,

    originalString = This is my secret text. No one except me can read it. My name is Magic Len, and I'm a 台灣人.
    originalHiddenString = DBADYWBLRSNYWBLTONYWBLTPLYWBLUNLYWBLNQRYWBLTPLYWBGHLUNLYWBLNQRYWBLTSNYWBLURTYWBLNQRYWBLUNLYWBLTLTYWBLSUNYWBLUMNYWBLTLTYWBLUNTYWBLNQRYWBLUNTYWBLTLTYWBLURLYWBLUNTYWBLORTYWBLNQRYWBLRNPYWBLTTTYWBLNQRYWBLTTTYWBLTTLYWBLTLTYWBLNQRYWBLTLTYWBLURLYWBLSUNYWBLTLTYWBLTURYWBLUNTYWBLNQRYWBLTSNYWBLTLTYWBLNQRYWBLSUNYWBLSSRYWBLTTLYWBLNQRYWBLUMNYWBLTLTYWBLSSRYWBLTLLYWBLNQRYWBLTPLYWBLUNTYWBLORTYWBLNQRYWBLRMRYWBLURTYWBLNQRYWBLTTLYWBLSSRYWBLTSNYWBLTLTYWBLNQRYWBLTPLYWBLUNLYWBLNQRYWBLRMRYWBLSSRYWBLTNPYWBLTPLYWBLSUNYWBLNQRYWBLRLTYWBLTLTYWBLTTLYWBLOQNYWBLNQRYWBLSSRYWBLTTLYWBLTLLYWBLNQRYWBLQTPYWBLOMNYWBLTSNYWBLNQRYWBLSSRYWBLNQRYBERRPQNPMRLYBBTURSUNYBERNPQNOSNTYWBLORTNZAOSYBBQNCDQSNZBVORYBWHWSDCZWUYBAHOCHNKZWIYWBVDQML
    compressedHiddenString = XPJBBNGMIMIMIMIMIMIMIYFUJIBOGADQAOAMDSCBHJDJCOBNIFCTHXIMEMHAHOAFDXGWJFIDJDAQBUBCHXCHHWIWDGJSEQEIINEAHWHTGFFBDOAMDAAAEWDLCGCMEGGAHRIQDRBUIQIKIYGBGLETDLDDCGEOADFAALHPFSGNDAGGFPEQBLAUEIGDCZCRHKHFDMFXHLCCAVJNCKABCKDVJQEIBKEQCSGQCVCBBMCLDTFEIUGYBKDLEMGQELEYIXIUISCYEFBEDBJREMHBICEYFCJCAOJYFLIHIYEFIYCIJPBBIDIBCXDHBTGAJYHKHQAIJKFTEFFZDCFPFWGUEOEZDEDJFCEIGLJYGTGNBDDABABTAYFTCCJNASBTEACIJQGACXEIGIAFCWJQJWIKDVILEHFUEUDCEPBYIIGLEXCBAIFQGLBJEMGUGVFMGFJNAMIMI
    decompressedHiddenString = DBADYWBLRSNYWBLTONYWBLTPLYWBLUNLYWBLNQRYWBLTPLYWBGHLUNLYWBLNQRYWBLTSNYWBLURTYWBLNQRYWBLUNLYWBLTLTYWBLSUNYWBLUMNYWBLTLTYWBLUNTYWBLNQRYWBLUNTYWBLTLTYWBLURLYWBLUNTYWBLORTYWBLNQRYWBLRNPYWBLTTTYWBLNQRYWBLTTTYWBLTTLYWBLTLTYWBLNQRYWBLTLTYWBLURLYWBLSUNYWBLTLTYWBLTURYWBLUNTYWBLNQRYWBLTSNYWBLTLTYWBLNQRYWBLSUNYWBLSSRYWBLTTLYWBLNQRYWBLUMNYWBLTLTYWBLSSRYWBLTLLYWBLNQRYWBLTPLYWBLUNTYWBLORTYWBLNQRYWBLRMRYWBLURTYWBLNQRYWBLTTLYWBLSSRYWBLTSNYWBLTLTYWBLNQRYWBLTPLYWBLUNLYWBLNQRYWBLRMRYWBLSSRYWBLTNPYWBLTPLYWBLSUNYWBLNQRYWBLRLTYWBLTLTYWBLTTLYWBLOQNYWBLNQRYWBLSSRYWBLTTLYWBLTLLYWBLNQRYWBLQTPYWBLOMNYWBLTSNYWBLNQRYWBLSSRYWBLNQRYBERRPQNPMRLYBBTURSUNYBERNPQNOSNTYWBLORTNZAOSYBBQNCDQSNZBVORYBWHWSDCZWUYBAHOCHNKZWIYWBVDQML
    recoveredString = This is my secret text. No one except me can read it. My name is Magic Len, and I'm a 台灣人.

# License

    Copyright 2015-2016 magiclen.org

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

# What's More?

Please check out our web page at

http://magiclen.org/string-hider/
