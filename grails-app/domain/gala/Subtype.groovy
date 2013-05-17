package gala

class Subtype {
    
    Type type
    Segment segment
    
    Integer pointsCount
    Boolean block
    Boolean restore
    
    static constraints = {
        segment nullable: true
    }
}
