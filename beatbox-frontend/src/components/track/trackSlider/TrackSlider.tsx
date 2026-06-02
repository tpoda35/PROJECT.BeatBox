import type { TrackSliderProps } from "./types/TrackSliderProps.ts";

import { Swiper, SwiperSlide } from 'swiper/react';
import { Navigation } from 'swiper/modules';
import 'swiper/css';
import 'swiper/css/navigation';
import * as React from "react";

const TrackSlider = ({ children }: TrackSliderProps) => {
    return (
            <Swiper
                modules={[Navigation]}
                navigation
                spaceBetween={10}
                breakpoints={{
                    0: { slidesPerView: 4, slidesPerGroup: 4 },
                    680: { slidesPerView: 5, slidesPerGroup: 5 },
                    939:  { slidesPerView: 4, slidesPerGroup: 4 },
                    1100:  { slidesPerView: 5, slidesPerGroup: 5 },
                    1250: { slidesPerView: 6, slidesPerGroup: 6 },
                }}
            >
                {/*{React.Children.map(children, (child, i) => (*/}
                {/*    <SwiperSlide key={i}><div>anyád</div></SwiperSlide>*/}
                {/*))}*/}
                {React.Children.map(children, (child, i) => (
                    <SwiperSlide key={i}>{child}</SwiperSlide>
                ))}
            </Swiper>
    );
};

export default TrackSlider;