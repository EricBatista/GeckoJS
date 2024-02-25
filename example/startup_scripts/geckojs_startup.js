StartupEvents.registry("item", event => {
    // Thanks for Enderman Overhaul's model and texture!
    event.create("geckojs:badlands_hood", "anim_chestplate")
        .geoModel(geo => {
            geo.setSimpleModel('geckojs:geo/armor/badlands_hood.geo.json')
            geo.setSimpleTexture('geckojs:textures/armor/badlands_hood.png')
        })
        .boneVisibility((renderer, slot) => {
            renderer.setAllVisible(false);

            if (slot == "chest") {
                renderer.setBoneVisible(renderer.getHeadBone(), true);
                renderer.setBoneVisible(renderer.getBodyBone(), true);
                renderer.setBoneVisible(renderer.getRightArmBone(), true);
                renderer.setBoneVisible(renderer.getLeftArmBone(), true);
            }
        })

    event.create("geckojs:example_item", "animatable")
        .addAnimation(state => state.setAndContinue(RawAnimation.begin().thenLoop("transforming")))
        .defaultGeoModel()

    event.create("geckojs:example_shield", "anim_shield")
        .addController(controller => controller
            .name("exp_controller")
            .triggerableAnim('using', RawAnimation.begin().thenPlay('expand'))
            .soundKeyframe(keyFrame => {
                if (Client.player) Client.player.playSound(keyFrame.keyframeData.sound)
            })
        )
        .usingAnimation((self, serverLevel, serverPlayer, hand) => {
            self.triggerAnim(serverPlayer, GeoItem.getOrAssignId(serverPlayer.getItemInHand(hand), serverLevel), 'exp_controller', 'using')
        })
        .defaultGeoModel()
})

StartupEvents.registry("block", event => {
    event.create("geckojs:example_block", "animatable")
        .cube(1, 1, 1, 15, 15, 15, true)
        .animatableBlockEntity(info => {
            info.addAnimation(state => state.setAndContinue(RawAnimation.begin().thenLoop("rotating")))
        })
        .defaultGeoModel()
})
