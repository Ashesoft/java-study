package com.longrise.spring.xhyl;
/**
 * ps: 当类中没有有参构造时, 系统会默认使用无参构造;
 *      当写了有参构造时, 没有写无参构造的话, 是访问不了无参构造的(反射也不可以);
 *      所以再写有参构造时同时也加上无参构造, 以免引起不必要的尴尬 
 */
public class B {
    private A a;
    // public B(){}
    // public B(A a){
    //     this.a = a;
    // }
    /**
     * @return the a
     */
    public A getA() {
        return a;
    }
}
