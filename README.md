# Gecko JS
Use GeckoLib to create animatable block/item/armor by KubeJS

## Simple Example
### [Startup script](https://github.com/DevDyna/GeckoJS/blob/2001/forge/example/startup_scripts/geckojs_startup.js)
```js
StartupEvents.registry("block", event => {
    event.create("geckojs:example_block", "animatable")
        .box(1, 1, 1, 15, 15, 15, true)
        .animatableBlockEntity(info => {
            info.addAnimation(state => state.setAndContinue(RawAnimation.begin().thenLoop("rotating")))
        })
        .defaultGeoModel()
})
```

### [Model](https://github.com/DevDyna/GeckoJS/blob/2001/forge/example/assets/geckojs/geo/block/example_block.geo.json) ```kubejs/assets/geckojs/geo/example_block.geo.json```
```json
{
	"format_version": "1.12.0",
	"minecraft:geometry": [
		{
			"description": {
				"identifier": "geometry.example_block",
				"texture_width": 48,
				"texture_height": 48,
				"visible_bounds_width": 3,
				"visible_bounds_height": 2.5,
				"visible_bounds_offset": [0, 0.75, 0]
			},
			"bones": [
				{
					"name": "bone",
					"pivot": [0, 8, 0],
					"cubes": [
						{
							"origin": [-7, 1, -7],
							"size": [14, 14, 14],
							"uv": {
								"north": {"uv": [0, 0], "uv_size": [14, 14]},
								"east": {"uv": [0, 14], "uv_size": [14, 14]},
								"south": {"uv": [14, 0], "uv_size": [14, 14]},
								"west": {"uv": [14, 14], "uv_size": [14, 14]},
								"up": {"uv": [0, 28], "uv_size": [14, 14]},
								"down": {"uv": [28, 14], "uv_size": [14, -14]}
							}
						}
					]
				}
			]
		}
	]
}
```

### [Animation](https://github.com/DevDyna/GeckoJS/blob/2001/forge/example/assets/geckojs/animations/block/example_block.animation.json) ```kubejs/assets/geckojs/animations/example_block.animation.json```
```json
{
	"format_version": "1.8.0",
	"animations": {
		"rotating": {
			"loop": true,
			"animation_length": 3,
			"bones": {
				"bone": {
					"rotation": {
						"vector": ["Math.sin(query.anim_time*90)*180", "Math.cos(query.anim_time*90)*180", "Math.sin(query.anim_time*90)*180"]
					}
				}
			}
		}
	},
	"geckolib_format_version": 2
}
```

### [Texture](https://github.com/DevDyna/GeckoJS/blob/2001/forge/example/assets/geckojs/textures/block/example_block.png) ```kubejs/assets/geckojs/textures/example_block.png```
![example_block.png](https://cdn.modrinth.com/data/cached_images/88fd8b077644601800dd7aa4585818dd68c19ea1.png)
