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
