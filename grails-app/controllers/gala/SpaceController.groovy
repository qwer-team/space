package gala

class SpaceController {

    def index() { 
        [segments: Segment.list()]
    }
}
