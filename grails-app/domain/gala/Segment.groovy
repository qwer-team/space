package gala

class Segment {
    static hasMany = [subtypes: Subtype]
    Integer start
    Integer end
    Integer length
    
    def getSubelements(){
        subtypes
    }
}
