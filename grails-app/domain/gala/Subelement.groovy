package gala

class Subelement {
    PrizeSegment segment
    PrizeElement element
    Integer prizeCount
    Boolean restore
    Boolean single = false
    Integer pointId
    
    static constraints = {
        pointId nullable: true
        segment nullable: true
    }
}

