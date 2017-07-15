# Chapter 6 Objects

### Q.1<br>

```scala

object Conversions {
 def inchesToCentimeters(inches:Int) = inches * 2.54
 def gallonsToLiters(gallons:Int) = gallons * 4.54609
 def milesToKilometers(miles:Int) = miles * 1.609344
}
 
Conversions.milesToKilometers(13) 
```
<br>

### Q.2<br>

```scala
abstract class UnitConversion {
 def convert(units:Int) : Double
}

object InchesToCentimeters extends UnitConversion {
	override def convert(inches:Int) = inches * 2.54
}
object GallonsToLiters extends UnitConversion {
	override def convert(gallons:Int) = gallons * 4.54609
}
object MilesToKilometers extends UnitConversion {
	override def convert(miles:Int) = miles * 1.609344
}

InchesToCentimeters.convert(10)  
```
<br>

### Q.4<br>

```scala
class Point(x:Double, y:Double)

var p = new Point(1,2) 

object Point {
  def apply(x:Double, y:Double) = new Point(x,y)
}

// now with companion object we can do the following - no 'new'
p = Point(2,3) 
```
<br>

### Q.5<br>

```scala

object Reverse extends App {  
  println(args.reverse mkString " ")
}

```
<br>

