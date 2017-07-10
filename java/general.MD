# Questions that don't really fit into the main categories

### Q. Can you explain the *transient* keyword and when you might make sure of it?<br>
According to the [Java Language Spec. as of writing Java-8](http://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html#jls-8.3.1.3), variables may be marked transient to indicate that they are not part of the persistent state of an object.<br>But what's that mean?<br>Well, suppose you've some serializable object with say a password field. You don't really want that field to be serialized when writing the object to a stream - say a file. If you mark the password field as transient then when the object is deserialized, that password field will be null.<br>
```java
public class SomeUser implements Serializable {
  String username;
  transient String password; // => when read back from stream, this field will be null
  ...
  blah blah blah
  ...
}
```


