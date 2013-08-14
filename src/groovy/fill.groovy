import gala.PrizeSegment
service = ctx.fillPrizeService

/*types = [2: 8, 3: 13]
service.fill(1, 30, types)*/
//fill 1000001, 1000000,  
segment = PrizeSegment.get(6)
service.fillSegment(segment)
