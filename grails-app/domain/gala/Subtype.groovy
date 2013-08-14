package gala

class Subtype {
    
    Type type
    Segment segment
    
    Integer pointsCount
    Boolean block
    Boolean restore
    Integer parameter
    
    static constraints = {
        segment nullable: true
        parameter nullable: true
    }
}
