package fr.epita.android.hellogame

data class ToDoObject ( val id : Int,val name : String,val picture : String) {



}
data class GameDetaliObject(val id:Int,val name:String, val type:String, val player:Int,val year: Int,val url:String,
                            val picture: String,val description_fr:String,val description_en: String){

}