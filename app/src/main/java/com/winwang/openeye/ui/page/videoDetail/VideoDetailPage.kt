package com.winwang.openeye.ui.page.videoDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.winwang.openeye.base.lifecycle.AppLifecycleObserver
import com.winwang.openeye.base.viewmodel.BaseViewModel
import com.winwang.openeye.base.viewmodel.ViewStateMutableLiveData
import com.winwang.openeye.ext.logD
import com.winwang.openeye.http.apiservice.ApiService
import com.winwang.openeye.model.VideoDetailDataModel
import com.winwang.openeye.ui.page.videoDetail.component.VideoDetailBottomComponent
import com.winwang.openeye.ui.theme.color_black
import com.winwang.openeye.widget.CommonTopAppBar
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 *
 * @Desc     -
 * @author   WinWang
 * @date     2023/12/11
 * @version
 */
@Composable
fun VideoDetailPage(
    relationId: Int,
    playUrl: String,
    viewmodel: VideoDetailViewModel = hiltViewModel()
) {
    viewmodel.relationID = relationId
    var playUrlState by remember { viewmodel.playUrlState }
    playUrlState = playUrl
    Column {
        CommonTopAppBar(title = "视频详情")
        HeaderPlayer(playUrlState)
        VideoDetailBottomComponent {
            playUrlState = it
        }
    }

}

@Composable
fun HeaderPlayer(playUrl: String) {
    "${playUrl}播放地址".logD()
    val context = LocalContext.current
    val exoPlayer = ExoPlayer.Builder(context)
        .build()
        .apply {
            playWhenReady = false
        }
    val mediaItem = MediaItem.fromUri(playUrl)
    exoPlayer.setMediaItem(mediaItem)
    exoPlayer.prepare()
    exoPlayer.play()
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = object : AppLifecycleObserver {
            override fun onPause(owner: LifecycleOwner) {
                exoPlayer.stop()
            }

            override fun onDestroy(owner: LifecycleOwner) {
                exoPlayer.release()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    PlayerSurface(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(color_black)
    ) {
        it.player = exoPlayer
    }
}


@Composable
fun PlayerSurface(
    modifier: Modifier = Modifier,
    onPlayerViewAvailable: (PlayerView) -> Unit = {}
) {
    AndroidView(
        factory = { context ->
            PlayerView(context).apply {
                useController = true
                onPlayerViewAvailable(this)
            }
        },
        modifier = modifier
    )
}


@HiltViewModel
class VideoDetailViewModel @Inject constructor(private var api: ApiService) : BaseViewModel() {

    var relationID: Int = -1

    val playIndex = mutableStateOf(-1)

    var playUrlState = mutableStateOf("")

    val videoDetailLiveData = ViewStateMutableLiveData<VideoDetailDataModel>()

    fun getVideoDetail() {
        launchApi(
            liveData = videoDetailLiveData,
            block = {
                api.getRelatedVideoList(relationID).apply { itemList = this.itemList?.filter { it?.type == "videoSmallCard" } }
            }
        )
    }

}
