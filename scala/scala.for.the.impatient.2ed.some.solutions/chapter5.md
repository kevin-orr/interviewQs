### Q.1<br>
By switching to using *Long* we can avoid the wrap around to negative numbers.
```scala

class Counter {
  private var value = 0L // You must initialize the field
  def increment() { value += 1L } // Methods are public by default
  def current() = value
}
 
```
<br>
