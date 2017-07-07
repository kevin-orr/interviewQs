### Q.1<br>

```scala

def randyArrayOfSize(n:Int) = for(i <- 0 until n; val r = Random) yield r.nextInt
 
```
<br>

### Q.2/3<br>

```scala

def swappy(a:Array[Int]) : Seq[Int]= {
  if(a.length % 2 == 0) (for(left <- 0 until (a.length, 2); right <- 1 to (0,-1)) yield a(left+right))
  else swappy(a.init) ++ Array(a.last)
} 

```
<br>

### Q.4<br>

```scala

def posNeg(aa:Array[Int]) = aa.filter(_ > 0) ++ aa.filter(_ <= 0)

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

