import "../styles/FooterComponentStyle.css";

const FooterComponent = () => {
    return (
      <div>
        <footer className='footer-component'>
                <div className='footer-container'>
                    <div className='footer-left'>
                    <h3 >Employee Portfolio Builder </h3> 
                    </div>
                    <div className='footer-right'>
                        <p className="copyright">Copyright 1998-2024. All rights reserved.</p>
                                <p className='footer-links'><a>Terms of Use </a> | 
                                                <a> Privacy </a> |
                                                <a> Security </a> |
                                                <a> Site Map </a> |
                                                <a> Accessibility </a> |
                                                <a> Contact Us </a>
                                </p>
                        <p className='us-only'><a>This is for persons in the U.S. only.</a></p>
                    </div>
                </div>
        </footer>
    </div>
    );
};
  
export default FooterComponent;