package com.example.projectnew21.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectnew21.dataClasses.ApiCard
import com.example.projectnew21.dataClasses.ApiCardList
import com.example.projectnew21.dataClasses.ApiDeck
import com.example.projectnew21.useCases.DeckUseCase
import org.koin.core.component.getScopeId

class GameViewModel(private val deckUseCase: DeckUseCase): ViewModel() {
    var deckLiveData= MutableLiveData<ApiDeck>()
    var cardListLiveData= MutableLiveData<ApiCardList>()
    var pontuacao = MutableLiveData<Int>(0)
    fun newDeck() {
        deckUseCase.newDeck { deckResponse->
            deckLiveData.value=deckResponse
            pontuacao.value = 0
        }
    }
    fun drawCard(){
        deckLiveData.value?.let {
            deckUseCase.drawCard(it){ apiCardList ->
                cardListLiveData.value=apiCardList
                cardListLiveData.value?.let {
                    calcularPontos(it.cards[0].value)
                }
            }
        }
    }

    fun calcularPontos(card: String){
        if (card.equals("2") || card.equals("3") || card.equals(
                "4"
            )
            || card.equals("5") || card.equals("6") || card.equals(
                "7"
            )
            || card.equals("8") || card.equals("9")
        ) {
            val parsedInt = card.toInt()
            pontuacao.value = pontuacao.value?.plus(parsedInt)
        } else if (card.uppercase().equals("A")) {
            pontuacao.value = pontuacao.value?.plus(1)
        } else {
            pontuacao.value = pontuacao.value?.plus(10)
        }
        Log.d("###############", "pontuacao1")
        Log.d("###############", pontuacao.value.toString())



    }
}