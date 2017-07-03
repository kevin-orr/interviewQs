# Some questions on Spring

* ### *Why don't we see any caching occurring?*(pcq)<br>
Image you've got some service with one method calling another that is annotated with <br>@Cacheable (org.springframework.cache.annotation.Cacheable) - similar to below.<br>Assumptions: your system is setup correctly for caching with Spring.<br>The *getMapByIds* method is an expensive operation which should cache the response (based on the id's) but for some reason it doesn't seem to be doing any caching. The entire operation is taking the same amount of time regardless of the caching annotation!<br>Why can't we get caching to work in this scenario?<br>
```java
@Service
public class MyService implements IMyService {

    public Map getMap() {
        // do some stuff to get id's - maybe we have to make a RESTful call to get the id's 
        // but we can't guaranteed what id's come back.
        // However, we do know that for whatever id's we have, the getMapByIds() method will
        // always give us back the same result for the same id's.
        Map map = getMapByIds(id1,id2);
        // maybe do some more work on map before finally returning it
        return map;
    }

    @Override
    @Cacheable(value = "myCache", key = "#root.method.name+'_'+#id1+'_'+#id2", unless = "#result == null")
    public Map getMapByIds(String id1, String id2) throws Exception{
        // maybe read from DB to get map based on id's...
        // whatever id's passed in always get same result for those id's - so we should be caching right?
        return result;
    }
    ...
}
```

<br>You need to remember that when Spring performs Caching, Transactions or any of the other niffy stuff it gives you, it's <br>
using AOP, and Spring AOP is proxy based. <br>
Spring will invoke methods on your object via a *proxy*. 
<br>However, as soon as you have *self-invocation* - calling your actual object but not via the proxy as we have <br>
with the *getMapByIds()* call, you're no longer going via the Spring proxy for you object and so you no longer get <br>
any of the goodies that Spring offers - like caching! <br><br>
So Spring suggests that you either:
* Refactor your code, or
* Use the AOPContext
<br>
The first option might be the best because the second option requires exposing the AOP context which even Spring reckons is expensive.
```java
    public Map getMap() {
        // do some stuff to get id's - maybe we have to make a RESTful call to get the id's 
        // but we can't guaranteed what id's come back.
        // However, we do know that for whatever id's we have, the getMapByIds() method will
        // always give us back the same result for the same id's.
        
        // now if we go via the Spring proxy (setting expose-proxy="true") the caching will work but at a cost.
        Map map = ((IMyService) AopContext.currentProxy()).getMapByIds(id1,id2);
        // maybe do some more work on map before finally returning it
        return map;
    }
}
```

