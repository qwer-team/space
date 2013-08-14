package gala

class PrizeSegment {
    static hasMany = [subelements: Subelement]
    Integer start
    Integer end
    Integer length
}
