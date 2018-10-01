package jsonlenium.config

import jsonlenium.constant.Driver
import jsonlenium.ui.util.WebDriverFactory
import org.openqa.selenium.phantomjs.PhantomJSDriverService
import org.openqa.selenium.remote.ProtocolHandshake

import java.util.logging.Level
import java.util.logging.Logger

waiting {
    timeout = 5
    interval = 1
}

environments {
    Logger.getLogger(ProtocolHandshake.class.getName()).setLevel(Level.OFF)
    Logger.getLogger(PhantomJSDriverService.class.getName()).setLevel(Level.OFF)

    chrome {
        driver = { WebDriverFactory.getDriver(Driver.CHROME) }
    }
    headlesschrome {
        driver = { WebDriverFactory.getDriver(Driver.HEADLESS_CHROME) }
    }
    phantomjs {
        driver = { WebDriverFactory.getDriver(Driver.PHANTOM_JS) }
    }
}
