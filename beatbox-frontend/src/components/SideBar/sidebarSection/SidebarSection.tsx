import type {SidebarSectionProps} from "./SidebarSectionProps.ts";

const SidebarSection = ({ title, children }: SidebarSectionProps) => {
    return (
        <div style={{marginBottom: "10px"}}>
            <h5>{title}</h5>
            <div>{children}</div>
        </div>
    );
};

export default SidebarSection;
