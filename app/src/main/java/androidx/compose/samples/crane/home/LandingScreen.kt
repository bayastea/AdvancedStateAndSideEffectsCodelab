/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package androidx.compose.samples.crane.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.samples.crane.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import kotlinx.coroutines.delay

private const val SplashWaitTime: Long = 2000

@Composable
fun LandingScreen(onTimeout: () -> Unit, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        // これは常に、LandingScreenが再構成された最新のonTimeout関数を参照する。
        // 副作用の進行中にonTimeOutが変更された時を考慮しているらしい
        // LaunchedEffectとともに使われることが一般的らしい
        val currentOnTimeout by rememberUpdatedState(onTimeout)

        // LandingScreenのライフサイクルに合ったエフェクトを作成する。
        // LandingScreenが再コンポーズするか、onTimeoutが変更された場合、 遅延が再び開始されることはありません。
        LaunchedEffect(Unit) { // 定数をキーとして使用することで、副作用を1回のみトリガーする
            delay(SplashWaitTime)
            currentOnTimeout()
        }

//            これは間違いらしい
//            LaunchedEffect(onTimeout) {
//                delay(SplashWaitTime) // Simulates loading things
//                onTimeout()
//            }

        Image(painterResource(id = R.drawable.ic_crane_drawer), contentDescription = null)
    }
}
