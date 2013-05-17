package gala

class Segment {
    static hasMany = [subtype: Subtype]
    Integer start
    Integer end
    Integer length
}
