# Checksum
### Argochamber interactive 2016
##### Java Library/Application

* * * 

## App Mode

This library can be run as an application uwing the command `java -jar Checksum.jar`.

It will list all files in the directory and then, print out their md5 checksum.

You can bulk print the checksums by invokating `java -jar Checksum.jar >> somefile.txt`

* * *

## Library Mode

If the jar `Checksum.jar` or the source code is implemented inside a java app, it will provide wrapper
functions for Checksum operations. Also has recusive scan of folders, if needed.

Provides a Checksum object, can be used inside the wrapper class, or either use the static method
`Checksum.getMD5Checksum(filepath)` or, for the raw version: `Checksum.createChecksum(path)`.

Both return a String with the checksum, either in hexadecimal or binary.
To use the wrapper class just create a Checksum object like:
```java
package test;

import com.argochamber.security.integrity.Checksum;

public class Test {
	
	public static void main(String[] args){
		
		Checksum sum = new Checksum("some_file.txt");
		
		System.out.println("The checksum is: "+sum.getMd5Sum());
		
	}
	
}
```