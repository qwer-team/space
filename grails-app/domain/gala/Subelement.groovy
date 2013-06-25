package gala

class Subelement {
    PrizeElement element
    Integer prizeCount
    Boolean restore
    Boolean single = false
    Integer pointId
    
    static constraints = {
        pointId nullable: true
    }
}

