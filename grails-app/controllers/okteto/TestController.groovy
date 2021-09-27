package okteto

class TestController {

    def index() {
        println "into the controller"
        render "Hello World"
    }
}