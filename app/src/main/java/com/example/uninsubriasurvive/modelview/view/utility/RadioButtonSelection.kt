package com.example.uninsubriasurvive.modelview.view.utility

sealed class RadioButtonSelection(val filter: String) {
    object ShowInterested: RadioButtonSelection("interest")
    object ShowMaybeInterested: RadioButtonSelection("maybe")
    object ShowNotInterested: RadioButtonSelection("not_interest")
}
