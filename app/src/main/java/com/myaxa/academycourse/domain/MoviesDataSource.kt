package com.myaxa.academycourse.domain

import com.myaxa.academycourse.data.models.Actor
import com.myaxa.academycourse.data.models.Movie

class MoviesDataSource {
    fun getMovies(): List<Movie> {
        return listOf<Movie>(
                Movie(0, "Mstuni", "After the devastating events of Avengers: Infinity War, the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos\\' actions and restore balance to the universe.",
                        "https://clck.ru/WStsK",
                        listOf(
                                Actor("Robert Downew J", "https://image.ibb.co/buLLjy/Ryan_Gosling.jpg"),
                                Actor("Robert Downew J", "https://image.ibb.co/buLLjy/Ryan_Gosling.jpg"),
                                Actor("Robert Downew J", "https://image.ibb.co/buLLjy/Ryan_Gosling.jpg"),
                                Actor("Robert Downew J", "https://image.ibb.co/buLLjy/Ryan_Gosling.jpg"),
                                Actor("Robert Downew J", "https://image.ibb.co/buLLjy/Ryan_Gosling.jpg"),
                        )
                ),
                Movie(1, "Tenet", "Prikoldes", "https://clck.ru/WSuNZ",
                        listOf(
                                Actor("Robert Downew J", "https://image.ibb.co/buLLjy/Ryan_Gosling.jpg"),
                                Actor("Robert Downew J", "https://image.ibb.co/buLLjy/Ryan_Gosling.jpg"),
                                Actor("Robert Downew J", "https://image.ibb.co/buLLjy/Ryan_Gosling.jpg"),
                                Actor("Robert Downew J", "https://image.ibb.co/buLLjy/Ryan_Gosling.jpg"),
                                Actor("Robert Downew J", "https://image.ibb.co/buLLjy/Ryan_Gosling.jpg"),
                        )
                ),
                Movie(2, "Black Widow", "Prikoldes", "https://clck.ru/WSuBW",
                        listOf(
                                Actor("Robert Downew J", "https://image.ibb.co/buLLjy/Ryan_Gosling.jpg"),
                                Actor("Robert Downew J", "https://image.ibb.co/buLLjy/Ryan_Gosling.jpg"),
                                Actor("Robert Downew J", "https://image.ibb.co/buLLjy/Ryan_Gosling.jpg"),
                                Actor("Robert Downew J", "https://image.ibb.co/buLLjy/Ryan_Gosling.jpg"),
                                Actor("Robert Downew J", "https://image.ibb.co/buLLjy/Ryan_Gosling.jpg"),
                        )
                ),
                Movie(3, "Wonder Waflya 1984", "Prikoldes", "https://clck.ru/WSuZn",
                        listOf(
                                Actor("Robert Downew J", "https://image.ibb.co/buLLjy/Ryan_Gosling.jpg"),
                                Actor("Robert Downew J", "https://image.ibb.co/buLLjy/Ryan_Gosling.jpg"),
                                Actor("Robert Downew J", "https://image.ibb.co/buLLjy/Ryan_Gosling.jpg"),
                                Actor("Robert Downew J", "https://image.ibb.co/buLLjy/Ryan_Gosling.jpg"),
                                Actor("Robert Downew J", "https://image.ibb.co/buLLjy/Ryan_Gosling.jpg"),
                        )
                ),
                Movie(4, "Some movie", "Prikoldes", "https://image.ibb.co/buLLjy/Ryan_Gosling.jpg",
                        listOf(
                                Actor("Robert Downew J", "https://image.ibb.co/buLLjy/Ryan_Gosling.jpg"),
                                Actor("Robert Downew J", "https://image.ibb.co/buLLjy/Ryan_Gosling.jpg"),
                                Actor("Robert Downew J", "https://image.ibb.co/buLLjy/Ryan_Gosling.jpg"),
                                Actor("Robert Downew J", "https://image.ibb.co/buLLjy/Ryan_Gosling.jpg"),
                                Actor("Robert Downew J", "https://image.ibb.co/buLLjy/Ryan_Gosling.jpg"),
                        )
                ),
                Movie(5, "Some movie", "Prikoldes", "https://image.ibb.co/buLLjy/Ryan_Gosling.jpg",
                        listOf(
                                Actor("Robert Downew J", "https://image.ibb.co/buLLjy/Ryan_Gosling.jpg"),
                                Actor("Robert Downew J", "https://image.ibb.co/buLLjy/Ryan_Gosling.jpg"),
                                Actor("Robert Downew J", "https://image.ibb.co/buLLjy/Ryan_Gosling.jpg"),
                                Actor("Robert Downew J", "https://image.ibb.co/buLLjy/Ryan_Gosling.jpg"),
                                Actor("Robert Downew J", "https://image.ibb.co/buLLjy/Ryan_Gosling.jpg"),
                        )
                ),
                Movie(6, "Some movie with Ryan Gosling", "Prikoldesnoe kinco s Ryanom Goslingom", "https://image.ibb.co/buLLjy/Ryan_Gosling.jpg",
                        listOf(
                                Actor("Ryan Gosling", "https://image.ibb.co/buLLjy/Ryan_Gosling.jpg"),
                                Actor("Ryan Gosling", "https://image.ibb.co/buLLjy/Ryan_Gosling.jpg"),
                                Actor("Ryan Gosling", "https://image.ibb.co/buLLjy/Ryan_Gosling.jpg"),
                                Actor("Ryan Gosling", "https://image.ibb.co/buLLjy/Ryan_Gosling.jpg"),
                                Actor("Ryan Gosling", "https://image.ibb.co/buLLjy/Ryan_Gosling.jpg"),
                                Actor("Ryan Gosling", "https://image.ibb.co/buLLjy/Ryan_Gosling.jpg"),
                                Actor("Ryan Gosling", "https://image.ibb.co/buLLjy/Ryan_Gosling.jpg"),
                        )
                ),
        )
    }
}