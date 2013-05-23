package gala

class Type {
    
    static hasMany = [subtypes: Subtype]
    
    String name
    String tag
    String message
    
    static constraints = {
        message nullable: true
        tag nullable: true
    }
}

