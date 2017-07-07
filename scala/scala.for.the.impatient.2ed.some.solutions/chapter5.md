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

### Q.2<br>

```scala

class BankAccount {
  private[this] var onDeposit:Double = 0.0  
  def balance = onDeposit
  def deposit(deposit:Double) = onDeposit = (onDeposit + deposit)
  def withdraw(withdraw:Double) :Double = {
    onDeposit = onDeposit - withdraw
    onDeposit
  }
  override def toString = s"Current Balance on deposit = $onDeposit"
}
 
```
<br>
