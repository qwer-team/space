import gala.Type

Type.list().each{
    it.delete()
}
types = [
    [name: "Черные", tag: 'black'],
    [name: "Ловушка", tag: 'trap'],
    [name: "+%", tag: 'plus_percent'],
    [name: "-%", tag: 'minus_percent'],
    [name: "Кража флиппера", tag: 'theft'],
    [name: "Точка ноль", tag: 'zero_point'],
    [name: "Ноль очков", tag: 'nil'],
    [name: "Кража флиппера", tag: 'theft'],
    [name: "Супер прыжек", tag: 'jump'],
    [name: "+ Период приза", tag: 'plus_prize_period'],
    [name: "- Период приза", tag: 'minus_prize_period'],
    [name: "+ Период для всех", tag: 'plus_all_period'],
    [name: "- Период для всех", tag: 'minus_all_period'],
]

types.each{
    new Type(name: it.name, tag: it.tag).save()
}
