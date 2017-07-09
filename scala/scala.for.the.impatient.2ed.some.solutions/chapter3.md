### Q.1<br>

```scala

def randyArrayOfSize(n:Int) = for(i <- 0 until n; val r = Random) yield r.nextInt
 
```

In Groovy:
```groovy
def randyArrayOfSize = { n -> (0..<n).collect { (new Random()).nextInt() } }
randyArrayOfSize(10)
```

<br>

### Q.2/3<br>

```scala

def swappy(a:Array[Int]) : Seq[Int]= {
  if(a.length % 2 == 0) (for(left <- 0 until (a.length, 2); right <- 1 to (0,-1)) yield a(left+right))
  else swappy(a.init) ++ Array(a.last)
} 

```
In Groovy:
```groovy
def swappy
swappy = { a ->
    if(a.size() % 2 == 0) (0..< a.size()).collect { index -> a[index % 2 == 0 ? index + 1 :index - 1]}
    else swappy(a.init()) + a.last()
}
```
<br>

### Q.4<br>

```scala

def posNeg(aa:Array[Int]) = aa.filter(_ > 0) ++ aa.filter(_ <= 0)

```
In Groovy:
```groovy
def posNeg = { a -> a.grep { it >= 0 } + a.grep { it < 0 } }
```
<br>


### Q.5<br>

```scala

def avg(a:Array[Double]) = if(a.length != 0) a.sum/a.length else 0

```
<br>


### Q.6<br>

```scala

val array = Array(1,2,3,4,5)                      //> array  : Array[Int] = Array(1, 2, 3, 4, 5)
array.sorted.reverse                              //> res0: Array[Int] = Array(5, 4, 3, 2, 1)
val arrayBuffer = ArrayBuffer(1,2,3,4,5)          //> arrayBuffer  : scala.collection.mutable.ArrayBuffer[Int] = ArrayBuffer(1, 2,
arrayBuffer.sorted.reverse                        //> res1: scala.collection.mutable.ArrayBuffer[Int] = ArrayBuffer(5, 4, 3, 2, 1)

```
<br>

### Q.7<br>

```scala

Array(1,2,1, 4, 4, 3,4,5).distinct

```
<br>


### Q.8<br>

```scala

def positivesAndFirstNeg(a:ArrayBuffer[Int]) = {
  val ignore = (for(i <- a.indices if(a(i)) < 0) yield i).drop(1).reverse
  for(i <- ignore) a.remove(i)
}

```
<br>

### Q.9<br>

```scala
def positivesAndFirstNeg(a:ArrayBuffer[Int]) = {
  val ignore = (for(i <- a.indices if(a(i)) < 0) yield i).drop(1).reverse
  for(i <- a.indices if(!ignore.contains(i))) yield a(i)
} 

```
<br>or, from my [Scala interview type questions](../README.md)<br>

```scala
def positivesAndFirstNeg(array: Array[Int]) : Array[Int] = {
  val (up2AndIncludingFirstNegative, afterFirstNegative) = array.splitAt(array.indexWhere(_ < 0) + 1)
  up2AndIncludingFirstNegative ++ (for(i <- afterFirstNegative if(i >= 0)) yield i)
}

```
<br>

### Q.10<br>

```scala
java.util.TimeZone.getAvailableIDs
		  .filter(_.contains("America"))
		  .map(_.replace("America/", ""))
		  .sorted
```
<br>

### Q.11<br>
On my system (JDK-8u121) I get back a map, hence the call to get values etc.
```scala
for(valu <- flavors.getNativesForFlavors(Array(DataFlavor.imageFlavor)).values.toArray) yield valu

```
<br>
