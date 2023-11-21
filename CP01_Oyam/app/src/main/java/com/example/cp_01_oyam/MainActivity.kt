package com.example.cp_01_oyam

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.assets.RenderableSource
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var renderableSource = RenderableSource.builder()
            .setSource(
                this,
                Uri.parse("CP-00_3D-Model.glb"),
                RenderableSource.SourceType.GLB
            )
            .setRecenterMode(RenderableSource.RecenterMode.ROOT)
            .build()

        ModelRenderable.builder()
            .setSource(
                this,
                renderableSource
            )
            .build()
            .thenAccept { modelRenderable ->
                var renderable = modelRenderable
            }
            .exceptionally { throwable ->
                return@exceptionally null;

            }
        var fragment = supportFragmentManager.findFragmentById(R.id.arViewFragment)
                as ArFragment

        fragment.setOnTapArPlaneListener { hitResult, _, _ ->
            val anchorNode = AnchorNode(hitResult.createAnchor())
            anchorNode.setParent(fragment.arSceneView.scene)
            val transformableNode = TransformableNode(fragment.transformationSystem)
            renderable = transformableNode.renderable
            transformableNode.setParent(anchorNode)
            transformableNode.select()
    }
}
}