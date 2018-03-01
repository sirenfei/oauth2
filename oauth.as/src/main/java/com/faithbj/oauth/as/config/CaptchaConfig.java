package com.faithbj.oauth.as.config;

import java.awt.Color;
import java.awt.Font;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.octo.captcha.component.image.backgroundgenerator.UniColorBackgroundGenerator;
import com.octo.captcha.component.image.color.SingleColorGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.DecoratedRandomTextPaster;
import com.octo.captcha.component.image.textpaster.textdecorator.BaffleTextDecorator;
import com.octo.captcha.component.image.textpaster.textdecorator.TextDecorator;
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.engine.GenericCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;
import com.octo.captcha.service.multitype.GenericManageableCaptchaService;

@Configuration
public class CaptchaConfig {

    @Bean
    public java.awt.Color colorBlack() {
        Color color = new Color(50, 50, 50);
        return color;
    }

    @Bean
    public java.awt.Color colorWrite() {
        Color color = new Color(255, 250, 240);
        return color;
    }

    @Bean
    public SingleColorGenerator colorGen() {
        SingleColorGenerator color = new SingleColorGenerator(colorBlack());
        return color;
    }

    @Bean
    public BaffleTextDecorator baffleDecorator() {
        BaffleTextDecorator baffleDecorator = new BaffleTextDecorator(1, colorWrite());
        return baffleDecorator;
    }

    @Bean
    public DecoratedRandomTextPaster decoratedPaster() {// 最大字符长度,文本颜色,文本混淆
        // DecoratedRandomTextPaster paster = new DecoratedRandomTextPaster(4, 4, colorGen());
        TextDecorator[] decorator = { baffleDecorator() };
        DecoratedRandomTextPaster paster = new DecoratedRandomTextPaster(4, 4, colorGen(), decorator);
        return paster;
    }

    @Bean
    public UniColorBackgroundGenerator backGenUni() {// 背景宽度,背景高度
        UniColorBackgroundGenerator backGenUni = new UniColorBackgroundGenerator(110, 40);
        return backGenUni;
    }

    @Bean
    public RandomFontGenerator fontGenRandom() {// 最小字体,最大字体
        Font[] fonts = { new Font("Arial", 0, 25) };
        RandomFontGenerator fontGenRandom = new RandomFontGenerator(21, 31, fonts);
        return fontGenRandom;
    }

    @Bean
    public ComposedWordToImage wordtoimage() {//
        ComposedWordToImage wordtoimage = new ComposedWordToImage(fontGenRandom(), backGenUni(), decoratedPaster());
        return wordtoimage;
    }

    @Bean
    public RandomWordGenerator wordgen() {//
        RandomWordGenerator wordgen = new RandomWordGenerator("aabbccddeefgghhkkmnnooppqqsstuuvvwxxyyzz");
        return wordgen;
    }

    @Bean
    public GimpyFactory captchaFactory() {//
        GimpyFactory captchaFactory = new GimpyFactory(wordgen(), wordtoimage());
        return captchaFactory;
    }

    @Bean
    public GenericCaptchaEngine imageEngine() {//
        GimpyFactory[] captchaFactories = { captchaFactory() };
        GenericCaptchaEngine imageEngine = new GenericCaptchaEngine(captchaFactories);
        return imageEngine;
    }

    @Bean
    public GenericManageableCaptchaService captchaService() {
        GenericManageableCaptchaService gmcs = new GenericManageableCaptchaService(imageEngine(), 180, 100000, 75000);
        return gmcs;
    }

}