import type { TrackSliderProps } from "./types/TrackSliderProps.ts";
import styles from './TrackSlider.module.css';

import { Swiper, SwiperSlide } from 'swiper/react';
import { Navigation } from 'swiper/modules';
import 'swiper/css';
import 'swiper/css/navigation';
import * as React from "react";

const TrackSlider = ({ children }: TrackSliderProps) => {
    return (
        <div className={styles.wrapper}>

                {React.Children.map(children, (child, i) => (
                    child
                ))}

            {/*<Swiper*/}
            {/*    modules={[Navigation]}*/}
            {/*    navigation*/}
            {/*    spaceBetween={10}*/}
            {/*    breakpoints={{*/}
            {/*        0: { slidesPerView: 4, slidesPerGroup: 4 },*/}
            {/*        680: { slidesPerView: 5, slidesPerGroup: 5 },*/}
            {/*        939:  { slidesPerView: 4, slidesPerGroup: 4 },*/}
            {/*        1100:  { slidesPerView: 5, slidesPerGroup: 5 },*/}
            {/*        1250: { slidesPerView: 6, slidesPerGroup: 6 },*/}
            {/*    }}*/}
            {/*>*/}
            {/*    {React.Children.map(children, (child, i) => (*/}
            {/*        <SwiperSlide key={i}>{child}</SwiperSlide>*/}
            {/*    ))}*/}
            {/*</Swiper>*/}
        </div>
    );
};

export default TrackSlider;