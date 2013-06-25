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
    Integer a1
    Integer a2
    Integer a3
    Integer b1
    Integer b2
    Integer b3
    Integer c1
    Integer c2
    Integer c3
    Integer delta1
    Integer delta2
    Integer delta3
    
    static constraints = {
        img1 nullable: true
        img2 nullable: true
    }
}
