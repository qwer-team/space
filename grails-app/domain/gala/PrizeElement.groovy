package gala

class PrizeElement {

    Prize prize
    String name
    Integer available = 0
    Integer visible = 0
    Float price = 0
    Integer account = 1
    String img1
    String img2
    Integer movingVariant
    Boolean blocked = false
    Integer a1 = 5
    Integer a2 = 5
    Integer a3 = 5
    Integer b1 = 5
    Integer b2 = 5
    Integer b3 = 5
    Integer c1 = 5
    Integer c2 = 5
    Integer c3 = 5
    Integer delta1 = 25
    Integer delta2 = 25
    Integer delta3 = 25
    
    static constraints = {
        img1 nullable: true
        img2 nullable: true
    }
    static mapping = {
        sort "name"
    }
}
