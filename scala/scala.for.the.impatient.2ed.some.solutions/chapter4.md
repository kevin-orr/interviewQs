### Q.1<br>

```scala
val nonDiscountedPrices = Map(("iphone", 500.50), ("imac", 2040.99), ("apple", 0.50))
val discountedPrices = scala.collection.mutable.Map[String, Double]()
for((k,v) <- nonDiscountedPrices) discountedPrices(k) = v - v * 0.1

```
<br>

### Q.2<br>
I'm going to suck in the text from *Project Gutenberg’s Alice’s Adventures in Wonderland, by Lewis Carroll*  
```scala
lazy val in = new java.util.Scanner(new java.net.URL("http://www.gutenberg.org/files/11/11-0.txt").openStream)
val map = scala.collection.mutable.Map[String, Int]()
while (in.hasNext()) {
  val word = in.next()
  map(word) = map.getOrElse(word, 0) + 1
}
map.toString
```
<br>
