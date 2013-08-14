package gala

class Point {
    Integer subtype
    Integer subelement
    static mapping = {
      version false
    }
    static constraints = {
        subelement nullable: true
    }
}
