package com.tck.beatbox

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class SoundViewModelTest {

    private lateinit var beatBox: BeatBox
    private lateinit var sound: Sound
    private lateinit var subject: SoundViewModel

    @Before  //Код, содержащийся в функции с пометкой @Before, будет выполнен один раз перед выполнением каждого теста.
    fun setUp() {
        beatBox = mock(BeatBox::class.java)
        sound = Sound("assetPath")
        subject = SoundViewModel(beatBox)
        subject.sound = sound
    }

    @Test
    fun exposesSoundNameAsTitle() {
        assertThat(subject.title, `is`(sound.name))
    }

    @Test
    fun callsBeatBoxPlayOnButtonClicked() {
        subject.onButtonClicked()
        // Функция verify(Object) объекта Mockito проверяет, вызывались ли эти функции так, как вы ожидали.
        verify(beatBox).play(sound)
    }
}